import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Client } from "src/app/interfaces/client.interface";
import { AuthService } from "src/app/service/data/auth.service";
import { ClientService } from "src/app/service/data/client.service";

/**
 * Componente de Perfil de Cliente
 * Maneja la visualización y gestión de datos del perfil del cliente incluyendo:
 * - Información personal (nombre, apellido, correo, teléfono, dirección)
 */
@Component({
  selector: "app-client-profile",
  templateUrl: "./client-profile.component.html",
  styleUrls: ["./client-profile.component.css"],
})
export class ClientProfileComponent implements OnInit {
  // Datos del cliente actual cargados desde la API
  client: Client | undefined;

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
  ) {}

  /**
   * Hook del ciclo de vida que inicializa el componente.
   * Carga los datos del cliente desde el ID de la ruta.
   */
  ngOnInit(): void {
    this.clientService.getMe().subscribe((client) => {
      if (!client) this.router.navigate(["/client/crud"]);
      else this.client = client;
    });
  }

  /**
   * Maneja el envío del formulario para actualizar la información del perfil del cliente.
   * Llama al servicio de cliente para actualizar y navega al perfil en caso de éxito.
   */
  onUpdate(): void {
    if (this.client) {
      this.clientService
        .update(this.client.id, this.client)
        .subscribe(() => {
          this.router.navigate([`/client`]);
        });
    }
  }

  /**
   * Maneja la eliminación de la cuenta con diálogo de confirmación.
   * Muestra alerta de confirmación, elimina la cuenta si se confirma y redirige a la página CRUD.
   */
  onDelete(): void {
    const confirmed = confirm(
      "¿Estás seguro de eliminar tu cuenta? Esta acción no se puede deshacer.",
    );
    if (this.client && confirmed) {
      this.clientService.delete(this.client.id).subscribe(() => {
        this.router.navigate(["/client/crud"]);
      });
    }
  }
}
