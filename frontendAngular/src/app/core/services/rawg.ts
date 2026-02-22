import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RawgService {
  private http = inject(HttpClient);
  private readonly API_KEY = '57390d0ee257464eb7d9295544da3eba';
  private readonly BASE_URL = 'https://api.rawg.io/api';

  // Obtener juegos populares para la sección /games
  getPopularGames(): Observable<any[]> {
    return this.http.get<any>(`${this.BASE_URL}/games?key=${this.API_KEY}&page_size=10&ordering=-rating`)
      .pipe(map(response => response.results));
  }

  // Obtener noticias o lanzamientos recientes para el Dashboard
  getLatestReleases(): Observable<any[]> {
    return this.http.get<any>(`${this.BASE_URL}/games?key=${this.API_KEY}&dates=2025-01-01,2026-12-31&ordering=-added`)
      .pipe(map(response => response.results));
  }
}