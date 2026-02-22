import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inventory } from '../models/inventory';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  private apiUrl = 'http://localhost:8080/inventory';

  constructor(private http: HttpClient) {}

  getMyInventory(): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(this.apiUrl, {
        withCredentials: true
    });
  }

  crearItem(item: Inventory): Observable<Inventory> {
    return this.http.post<Inventory>(this.apiUrl, item, {
      withCredentials: true
    });
  }

  eliminarItem(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      withCredentials: true
    });
  }
}
