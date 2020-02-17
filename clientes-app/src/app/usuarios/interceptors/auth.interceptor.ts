import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { AuthService } from '../auth.service'
import swal from 'sweetalert2';
import { catchError } from 'rxjs/operators'; // catcError se encarga de interseptar el flujo si falla
import { Router } from '@angular/router';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

constructor(private authService: AuthService, private router: Router){}

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {

    return next.handle(req).pipe(
      catchError(e =>{
        if(e.status==401){

          if (this.authService.isAuthenticated()) {
            this.authService.logout();
          }
    
          this.router.navigate(['/login']);
          //no esta autorizado y redirigir a login
        }
    
        if(e.status==403){
          swal('Acceso Denegado',`HOla ${this.authService.usuario.username} no tienes acceso a este Recurso!`,'warning');
          this.router.navigate(['/clientes']);
          //no esta autorizado y redirigir a login
        }
        return throwError(e);
      })
    );
    /*aca se esta manejando la respuesta osea cuando recibimos los datos
      se va a validar los codigos http 401 y 403  
    */
  }
}
