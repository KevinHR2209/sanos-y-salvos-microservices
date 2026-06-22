/**
 * Lambda: sanosysalvos-notificaciones
 * Trigger: Amazon SQS (cola sanosysalvos-notificaciones)
 * Propósito: Procesa mensajes de la cola y registra notificaciones
 *            cuando se crea un nuevo reporte de mascota perdida.
 */

const { SQSClient, DeleteMessageCommand } = require("@aws-sdk/client-sqs");

const sqsClient = new SQSClient({ region: process.env.AWS_REGION || "us-east-1" });

/**
 * Handler principal de la Lambda.
 * Se ejecuta automáticamente cuando llega un mensaje a la cola SQS.
 */
exports.handler = async (event) => {
  console.log("Lambda sanosysalvos-notificaciones iniciada");
  console.log("Mensajes recibidos:", event.Records.length);

  const resultados = [];

  for (const record of event.Records) {
    try {
      // Parsear el cuerpo del mensaje
      const cuerpo = JSON.parse(record.body);
      console.log("Procesando mensaje:", JSON.stringify(cuerpo));

      // Validar estructura del mensaje
      if (!cuerpo.tipo) {
        throw new Error("Mensaje sin campo 'tipo'");
      }

      // Procesar según el tipo de notificación
      switch (cuerpo.tipo) {
        case "REPORTE_CREADO":
          await procesarReporteCreado(cuerpo);
          break;

        case "COINCIDENCIA_ENCONTRADA":
          await procesarCoincidencia(cuerpo);
          break;

        case "MASCOTA_ENCONTRADA":
          await procesarMascotaEncontrada(cuerpo);
          break;

        default:
          console.log(`Tipo de notificación desconocido: ${cuerpo.tipo}`);
      }

      resultados.push({
        messageId: record.messageId,
        status: "OK"
      });

    } catch (error) {
      console.error("Error procesando mensaje:", record.messageId, error.message);
      resultados.push({
        messageId: record.messageId,
        status: "ERROR",
        error: error.message
      });
    }
  }

  console.log("Resultados:", JSON.stringify(resultados));
  return {
    statusCode: 200,
    body: JSON.stringify({
      mensaje: "Procesamiento completado",
      procesados: resultados.length,
      resultados
    })
  };
};

/**
 * Procesa notificación de nuevo reporte de mascota perdida.
 */
async function procesarReporteCreado(datos) {
  console.log("[REPORTE_CREADO] Nuevo reporte registrado:");
  console.log(`  - Mascota: ${datos.nombreMascota || "Sin nombre"} (${datos.especie || "Desconocida"})`);
  console.log(`  - Reportado por usuario ID: ${datos.usuarioId}`);
  console.log(`  - Zona: ${datos.zona || "No especificada"}`);
  console.log(`  - Timestamp: ${new Date().toISOString()}`);
  // Aquí se podría integrar con SES para enviar email, SNS para push, etc.
}

/**
 * Procesa notificación de coincidencia encontrada entre reportes.
 */
async function procesarCoincidencia(datos) {
  console.log("[COINCIDENCIA_ENCONTRADA] Se encontró una posible coincidencia:");
  console.log(`  - Reporte original ID: ${datos.reporteId}`);
  console.log(`  - Reporte coincidente ID: ${datos.reporteCoincidenteId}`);
  console.log(`  - Porcentaje de similitud: ${datos.similitud || "N/A"}%`);
}

/**
 * Procesa notificación de mascota encontrada.
 */
async function procesarMascotaEncontrada(datos) {
  console.log("[MASCOTA_ENCONTRADA] Mascota marcada como encontrada:");
  console.log(`  - Mascota ID: ${datos.mascotaId}`);
  console.log(`  - Usuario notificado ID: ${datos.usuarioId}`);
}
