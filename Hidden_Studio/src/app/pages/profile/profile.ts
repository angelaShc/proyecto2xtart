import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth';
import { UserDTO } from '../../core/models/user';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  template: `
  @if (user) {
    <div class="profile-card">
      <h1>Perfil de Usuario</h1>
      <p><strong>Username:</strong> {{ user.username }}</p>
      <p><strong>Email:</strong> {{ user.email }}</p>
      
      <h3>Roles</h3>
      <ul>
        @for (role of user.roles; track role) {
          <li>{{ role }}</li>
        }
      </ul>

      <h3>Lista de Amigos</h3>
      <div class="friends-list">
        @for (friend of user.friends; track friend.id) {
          <div class="friend-item">
            <span>+ {{ friend.username }}</span>
          </div>
        } @empty {
          <p>No tienes amigos agregados aún.</p>
        }
      </div>
    </div>
  }
  `
})

export class ProfileComponent implements OnInit {
  private authService = inject(AuthService);
  user: UserDTO | null = null;

  ngOnInit(): void {
    this.authService.getUserProfile().subscribe({
      next: (data) => this.user = data,
      error: (err) => console.error('Error al cargar perfil', err)
    });
  }
}