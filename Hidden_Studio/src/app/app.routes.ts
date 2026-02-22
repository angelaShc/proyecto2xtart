import { Routes } from '@angular/router';

import { authGuard } from './core/guards/auth';
import { roleGuard } from './core/guards/role';

import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { DashboardComponent } from './pages/dashboard/dashboard';
import { GameListComponent } from './pages/games/game-list/game-list';
import { GameDetailComponent } from './pages/games/game-detail/game-detail';
import { ProfileComponent } from './pages/profile/profile';

export const routes: Routes = [
    
  // --- RUTAS PÚBLICAS ---
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // --- RUTAS PROTEGIDAS ---
  { 
    path: 'dashboard', 
    component: DashboardComponent, 
    canActivate: [authGuard] 
  },
  { 
    path: 'games', 
    component: GameListComponent, 
    canActivate: [authGuard] 
  },
  { 
    path: 'games/:id', 
    component: GameDetailComponent, 
    canActivate: [authGuard] 
  },
  { 
    path: 'profile', 
    component: ProfileComponent, 
    canActivate: [authGuard] 
  },

  // --- REDIRECCIÓN POR DEFECTO ---
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' } 
];