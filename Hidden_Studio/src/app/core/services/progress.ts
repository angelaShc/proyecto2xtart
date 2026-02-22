import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GameProgressDTO } from '../models/game_progress';

@Injectable({
  providedIn: 'root'
})
export class ProgressService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/me/progress';

  getMyProgress(): Observable<GameProgressDTO[]> {
    return this.http.get<GameProgressDTO[]>(this.API_URL);
  }
  registrarAcceso(juegoId: string, puntos: number, progreso: number): Observable<any> {
    const params = new HttpParams()
      .set('juegoId', juegoId)
      .set('puntos', puntos.toString())
      .set('progreso', progreso.toString());
    
    return this.http.post(`${this.API_URL}/registrar`, null, { 
      params, 
      withCredentials: true 
    });
  }
  updateProgress(progress: GameProgressDTO): Observable<GameProgressDTO> {
    return this.http.post<GameProgressDTO>(this.API_URL, progress, {
      withCredentials: true
    });
  }

  iniciarSesionAutomatico(juegoId: string): Observable<any> {
  return this.http.post(`${this.API_URL}/start/${juegoId}`, {}, { withCredentials: true });
  }

  enviarPuntuacion(juegoId: string, puntos: number): Observable<any> {
  const params = { juegoId, puntos: puntos.toString(), progreso: '0' };
  return this.http.post(`${this.API_URL}/update`, null, { 
    params, 
    withCredentials: true 
  });
  }
}