import { Component, OnInit, OnDestroy, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { GameService } from '../../../core/services/game';
import { InventoryService } from '../../../core/services/inventory';

import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ProgressService } from '../../../core/services/progress';

@Component({
  selector: 'app-game-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './game-detail.html',
  styleUrls: ['./game-detail.css']
})
export class GameDetailComponent implements OnInit, OnDestroy {
  private route = inject(ActivatedRoute);
  private gameService = inject(GameService);
  private inventoryService = inject(InventoryService);
  private sanitizer = inject(DomSanitizer);
  private progressService = inject(ProgressService);

  game = signal<any | null>(null);
  isLoading = signal<boolean>(true);
  gameUrl: SafeResourceUrl | null = null;

  puntosActuales = signal<number>(0);
  progresoActual = signal<number>(0);

  private scoreListener = (event: any) => {
    const puntos = event.detail.puntos;
    this.puntosActuales.set(puntos);
    this.manejarPuntuacionUnity(puntos);
  };

  private progressListener = (event: any) => {
  const progreso = event.detail.progreso;
  this.progresoActual.set(progreso);
  this.manejarProgresoUnity(progreso);
  };

  //funcion futura
  private itemListener = (event: any) => this.handleItemFound(event);

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.gameService.getGameById(id).subscribe({
        next: (gameData) => {
          if (!gameData) {
            this.isLoading.set(false);
            return;
          }

          this.game.set(gameData);
          
          const idParaBackend = (gameData as any)._id || gameData.id;
          const path = gameData.urlWebGL;

          if (path && idParaBackend) {
            this.gameUrl = this.sanitizer.bypassSecurityTrustResourceUrl(path);

            this.progressService.iniciarSesionAutomatico(idParaBackend).subscribe({
              next: () => {
                this.progressService.getMyProgress().subscribe(progresos => {
                  const progresoJuego = progresos.find(p => p.juegoId === idParaBackend);
                  if (progresoJuego) {
                    setTimeout(() => {
                      window.dispatchEvent(new CustomEvent("loadCheckpoint", {
                        detail: { progreso: progresoJuego.progreso }
                      }));
                      console.log("Progreso enviado tras espera de seguridad");
                    }, 3000);
                  } 
                });
                this.manejarPuntuacionUnity(0);
              }
            });
          }
          this.isLoading.set(false);
        },
        error: (err) => {
          console.error('Error al cargar el juego:', err);
          this.isLoading.set(false);
        }
      });
    }

    window.addEventListener('unityScoreUpdate', this.scoreListener);
    window.addEventListener('gameItemFound', this.itemListener);
    window.addEventListener('unityProgressUpdate', this.progressListener);
  }

  ngOnDestroy(): void {
    window.removeEventListener('unityScoreUpdate', this.scoreListener);
    window.removeEventListener('gameItemFound', this.itemListener);
    window.removeEventListener('unityProgressUpdate', this.progressListener);
  }

  private manejarPuntuacionUnity(puntos: number): void {
    const juegoActual = this.game();
    if (!juegoActual) return;

    const idValido = (juegoActual as any)._id || juegoActual.id;

    if (idValido) {
      this.progressService.registrarAcceso(idValido, puntos, 0).subscribe({
        next: (res) => console.log(`Sincronizado: ${puntos} puntos.`, res),
        error: (err) => console.error('Error de CORS o Red al registrar:', err)
      });
    }
  }

  private handleItemFound(event: any): void {
    const itemData = event.detail;
    const juegoActual = this.game();

    if (!juegoActual) return;

    const idValido = (juegoActual as any)._id || juegoActual.id;

    if (idValido) {
      const nuevoItem = {
        juegoId: idValido,
        nombre: itemData.nombre,
        detalle: itemData.detalle
      };

      this.inventoryService.crearItem(nuevoItem).subscribe({
        next: () => alert(`¡Ítem guardado en tu inventario: ${itemData.nombre}!`),
        error: (err) => console.error('Error al guardar objeto:', err)
      });
    }
  }

  private manejarProgresoUnity(progreso: number): void {
  const juegoActual = this.game();
  if (!juegoActual) return;

  const idValido = (juegoActual as any)._id || juegoActual.id;
  
  if (idValido) {
    this.progressService.registrarAcceso(idValido, this.puntosActuales(), progreso).subscribe({
      next: (res) => console.log(`Progreso guardado: ${progreso}%`, res),
      error: (err) => console.error('Error al guardar progreso:', err)
    });
  }
}
}