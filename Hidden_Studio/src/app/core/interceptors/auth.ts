import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('auth_token');
  
console.log('Interceptor ejecutándose para:', req.url);

  if (token) {
    const cleanToken = token.replace('Bearer ', '').trim();
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${cleanToken}`
      }
    });
    console.log('Enviando Header:', cloned.headers.get('Authorization'));
    return next(cloned);
  }

  return next(req);
};