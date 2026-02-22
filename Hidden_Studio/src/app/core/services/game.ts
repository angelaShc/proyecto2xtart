import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from '../models/game';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/game';

  getGames(): Observable<Game[]> {
    return this.http.get<Game[]>(this.API_URL);
  }

  getGameById(id: string): Observable<Game> {
    return this.http.get<Game>(`${this.API_URL}/${id}`);
  }

  //solo admin
  createGame(game: Game): Observable<Game> {
    return this.http.post<Game>(this.API_URL, game);
  }

  deleteGame(id: string): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}