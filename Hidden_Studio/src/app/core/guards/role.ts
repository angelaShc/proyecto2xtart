import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  
  const expectedRole = route.data['role'];

  if (authService.isAuthenticated() && authService.hasRole(expectedRole)) {
    return true;
  }

  alert('No tienes permisos para acceder a esta sección');
  router.navigate(['/dashboard']);
  return false;
};