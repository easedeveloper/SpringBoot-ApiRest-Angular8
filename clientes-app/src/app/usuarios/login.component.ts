import { Component, OnInit } from '@angular/core';
import { Usuario } from './usuario';
import swal from 'sweetalert2';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  titulo:string = "Inicie Sesion";
  usuario: Usuario;

  constructor(private authService: AuthService, private router: Router) {
    this.usuario=new Usuario();
  }

  ngOnInit(){
    if(this.authService.isAuthenticated()){
      swal('Login', `Hola ${this.authService.usuario.username} ya estas autenticado!`,'info');
      this.router.navigate(['/clientes']);
    }
  }

  login():void{
    console.log("Los Datos del Usuario: ",this.usuario);
    if (this.usuario.username == null || this.usuario.password == null) {
      swal('Error Login','Username o Password vacías', 'error');
      return;
    }
    this.authService.login(this.usuario).subscribe(respuesta => {
      console.log("Soy el Token de acceso, Refresh Token, fecha que expira el token ",respuesta);
      /*let objetoPayload= (JSON.parse(atob(respuesta.access_token.split(".")[1]))); se quita porque se esta utilizando
      en la clase authservice.ts
      
      console.log("Soy el PayLoad= ", objetoPayload);      
      //function de JS atob, convertimos datos, pero con JSON.parse lo convertimos a OBJETO
      //lo que hace el split es convertir el string en un arreglo
      */
      this.authService.guardarUsuario(respuesta.access_token);
      this.authService.guardarToken(respuesta.access_token);
      let usuario = this.authService.usuario; //se maneja como un atritbuto
      //aqui se va a guardar el toke y el usuario

      this.router.navigate(['/clientes']);
      swal('Login', `Hola ${usuario.username}, has iniciado sesión con exito`, 'success')
    }, error =>{
       if(error.status == 400){
        swal('Error Login','Credenciales Incorrecta!', 'error');
       }
    });
  }

}
