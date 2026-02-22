import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { GameService } from '../../../core/services/game';
import { AuthService } from '../../../core/services/auth';
import { Game } from '../../../core/models/game';

import { RawgService } from '../../../core/services/rawg';

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './game-list.html',
  styleUrl: './game-list.css'
})
export class GameListComponent implements OnInit {
  private gameService = inject(GameService);
  public authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);
  private rawgService = inject(RawgService);

  games: Game[] = [];
  popularGames: any[] = [];

  ngOnInit(): void {
    this.gameService.getGames().subscribe({
      next: (data) => {
      console.log('Juegos recibidos:', data);
      this.games = [...data];
      this.cdr.detectChanges();
      },
      error: (err) => console.error('Error cargando juegos', err)
    });
    this.rawgService.getPopularGames().subscribe({
      next: (data) => {
        this.popularGames = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error cargando juegos populares', err)
    });
  }

  eliminarJuego(id: string) {
    if(confirm('¿Seguro que deseas eliminar este juego?')) {
      this.gameService.deleteGame(id).subscribe(() => {
        this.games = this.games.filter(g => g.id !== id);
        this.cdr.detectChanges();
      });
    }
  }
}