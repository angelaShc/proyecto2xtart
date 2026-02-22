import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  styleUrls: ['./navbar.css'],
  template: `
    <nav class="navbar">
  <div class="nav-container">
    <div class="logo" routerLink="/">
      Hidden<span>Studio</span>
    </div>
    
    <div class="menu">
      @if (authService.isAuthenticated()) {
        <div class="user-greeting">
          <span>Hola, <strong class="username">{{ authService.currentUser()?.username }}</strong></span>
        </div>

        <button class="nav-btn" routerLink="/dashboard">Dashboard</button>
        <button class="nav-btn" routerLink="/games">Juegos</button>
        
        @if (authService.hasRole('ADMIN')) {
          <button class="nav-btn admin-btn" routerLink="/admin/games">Panel Admin</button>
        }
        
        <button (click)="logout()" class="nav-btn logout-btn">Cerrar Sesión</button>
        
      } @else {
        <button class="nav-btn login-btn" routerLink="/login">Entrar</button>
        <button class="nav-btn register-btn" routerLink="/register">Unirse</button>
      }
    </div>
  </div>
</nav>
  `
})
export class NavbarComponent {
  public authService = inject(AuthService);

  logout() {
    this.authService.logout();
  }
}