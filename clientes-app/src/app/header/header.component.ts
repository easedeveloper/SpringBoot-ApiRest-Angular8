import { Component } from '@angular/core';
import { AuthService } from '../usuarios/auth.service';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
    selector: 'app-header',
    templateUrl:'./header.component.html'
})
export class HeaderComponent{

    title:string ='APP ANGULAR';

    constructor(private authservice: AuthService, private router: Router){}

    logout():void{
        let username = this.authservice.usuario.username;
        this.authservice.logout();
        swal('Logout', `Hola ${username} has cerrado sesion con exito!`, 'success');
        this.router.navigate(['/login']);
    }

}