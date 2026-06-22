# Lambda: sanosysalvos-notificaciones

Función serverless que procesa mensajes de la cola SQS `sanosysalvos-notificaciones`.

## Propósito

Actúa como **consumidor** de la cola SQS. Se activa automáticamente cuando `ms-notificaciones` (productor) envía un mensaje a la cola con información de eventos del sistema:
- Nuevo reporte de mascota perdida creado
- Coincidencia encontrada entre reportes
- Mascota marcada como encontrada

## Arquitectura del flujo

```
[ms-reportes] → POST reporte
      ↓
[ms-notificaciones] → SQS.sendMessage()
      ↓
[SQS: sanosysalvos-notificaciones]
      ↓ (trigger automático)
[Lambda: sanosysalvos-notificaciones] → procesa y loguea
```

## Crear la función en AWS (primera vez)

### 1. Crear rol IAM para la Lambda

```bash
# Crear el rol de ejecución
aws iam create-role \
  --role-name sanosysalvos-lambda-role \
  --assume-role-policy-document '{
    "Version": "2012-10-17",
    "Statement": [{
      "Effect": "Allow",
      "Principal": { "Service": "lambda.amazonaws.com" },
      "Action": "sts:AssumeRole"
    }]
  }'

# Adjuntar políticas necesarias
aws iam attach-role-policy \
  --role-name sanosysalvos-lambda-role \
  --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaSQSQueueExecutionRole

aws iam attach-role-policy \
  --role-name sanosysalvos-lambda-role \
  --policy-arn arn:aws:iam::aws:policy/CloudWatchLogsFullAccess
```

### 2. Empaquetar el código

```bash
cd lambda-notificaciones
zip -r lambda-notificaciones.zip index.js
```

### 3. Crear la función Lambda

```bash
aws lambda create-function \
  --function-name sanosysalvos-notificaciones \
  --runtime nodejs20.x \
  --role arn:aws:iam::339713059214:role/sanosysalvos-lambda-role \
  --handler index.handler \
  --zip-file fileb://lambda-notificaciones.zip \
  --timeout 30 \
  --memory-size 128 \
  --region us-east-1
```

### 4. Conectar la cola SQS como trigger

```bash
# Obtener el ARN de la cola
QUEUE_URL=$(aws sqs get-queue-url --queue-name sanosysalvos-notificaciones --query 'QueueUrl' --output text)
QUEUE_ARN=$(aws sqs get-queue-attributes --queue-url $QUEUE_URL --attribute-names QueueArn --query 'Attributes.QueueArn' --output text)

# Agregar la cola como trigger de la Lambda
aws lambda create-event-source-mapping \
  --function-name sanosysalvos-notificaciones \
  --event-source-arn $QUEUE_ARN \
  --batch-size 10
```

### 5. Probar manualmente

```bash
aws lambda invoke \
  --function-name sanosysalvos-notificaciones \
  --payload '{
    "Records": [{
      "messageId": "test-001",
      "body": "{\\"tipo\\": \\"REPORTE_CREADO\\", \\"nombreMascota\\": \\"Firulais\\", \\"especie\\": \\"Perro\\", \\"usuarioId\\": 1, \\"zona\\": \\"Valparaiso\\"}"
    }]
  }' \
  --cli-binary-format raw-in-base64-out \
  response.json && cat response.json
```

## Variables de entorno

| Variable | Valor | Descripción |
|---|---|---|
| `AWS_REGION` | `us-east-1` | Región AWS |
