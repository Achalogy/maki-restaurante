import { OrderStatus } from "src/app/interfaces/purchase-order.interface";

// Literalmente solo traduce el estado
export default (status: OrderStatus) => {
    switch(status) {
      case "pending": return "Pendiente";
      case "preparation": return "En preparación";
      case "sent": return "Enviado";
      case "delivered": return "En camino";
      case "cancelled": return "Cancelado"
      case "completed": return "Completado"
    }
  }