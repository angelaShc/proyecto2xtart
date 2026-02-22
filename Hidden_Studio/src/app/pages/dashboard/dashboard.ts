import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProgressService } from '../../core/services/progress';
import { GameProgressDTO } from '../../core/models/game_progress';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  styleUrls: ['./dashboard.css'],
  template: `
    <div class="dashboard-container">
    <header class="dashboard-header">
      <h1>Mi <span>Progreso</span></h1>
      <div class="header-line"></div>
    </header>
      
      @if (progressList.length > 0) {
      <div class="table-responsive">
        <table class="stats-table">
          <thead>
            <tr>
              <th>Juego ID</th>
              <th>Puntos</th>
              <th>Progreso</th>
              <th>Tiempo Jugado</th>
            </tr>
          </thead>
          <tbody>
            @for (p of progressList; track p.juegoId) {
              <tr class="stats-row">
                <td class="game-id">{{ p.juegoId }}</td>
                <td class="points">{{ p.puntos }} XP</td> <td class="progress-text">{{ p.progreso }}%</td> <td class="time">{{ p.tiempoJugado }} s</td>
              </tr>
            }
          </tbody>
        </table>
      </div>
      } @else {
        <div class="empty-state">
          <p>Aún no tienes progreso registrado. ¡Empieza a jugar!</p>
          <button routerLink="/games" class="play-now-btn">Explorar Juegos</button>
        </div>
      }
    </div>
  `
})
export class DashboardComponent implements OnInit {
  private progressService = inject(ProgressService);
  progressList: GameProgressDTO[] = [];

  ngOnInit(): void {
    this.progressService.getMyProgress().subscribe({
      next: (data) => this.progressList = data,
      error: (err) => console.error('Error al obtener progreso', err)
    });
  }
}