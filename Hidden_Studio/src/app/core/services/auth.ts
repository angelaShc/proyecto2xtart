import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap, catchError, throwError } from 'rxjs';

import { User, UserDTO } from '../models/user';
import { LoginRequest, LoginResponse } from '../models/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  private readonly AUTH_URL = 'http://localhost:8080/auth';
  private readonly USER_URL = 'http://localhost:8080/user';

  private currentUserSignal = signal<LoginResponse | null>(this.getUserFromStorage());

    currentUser = computed(() => this.currentUserSignal());

    isAuthenticated = computed(() => !!this.currentUserSignal());

    constructor() {}

    //metodos de autenticacion
    register(user: UserDTO): Observable<User> {
        return this.http.post<User>(`${this.AUTH_URL}/register`, user);
    }

    login(credentials: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${this.AUTH_URL}/login`, credentials).pipe(
          tap((response: LoginResponse) => {
            this.currentUserSignal.set(response);
            localStorage.setItem('user', JSON.stringify(response));
            localStorage.setItem('auth_token', response.token);
          }),
          catchError(error => {
            console.error('Login error:', error);
            return throwError(() => new Error('Login failed. Please check your credentials and try again.'));
          })
        );
    }

    logout(): void {
        this.currentUserSignal.set(null);
        localStorage.removeItem('user');
        localStorage.removeItem('auth_token');
        this.router.navigate(['/login']);
    }

    //metodos de utilidad
    getUserProfile(): Observable<UserDTO> {
        return this.http.get<UserDTO>(this.USER_URL, { withCredentials: true });
    }
    hasRole(role: string): boolean {
        return this.currentUserSignal()?.roles.includes(role) || false;
    }

    private getUserFromStorage(): LoginResponse | null {
      const user = localStorage.getItem('user');
      if (user) {
        try {
          return JSON.parse(user);
        } catch (e) {
          localStorage.removeItem('user');
          return null;
        }
      }
      return null;
    }

    getToken(): string | null {
      return localStorage.getItem('auth_token');
    }
}