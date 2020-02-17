import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DirectivaComponent } from './directiva/directiva.component';
import { ClientesComponent } from './clientes/clientes.component';
import { PaginatorComponent } from './paginator/paginator.component';
import { FormularioComponent } from './clientes/formulario.component';
import { ClienteService } from './clientes/cliente.service';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { registerLocaleData } from '@angular/common';
import localeES from '@angular/common/locales/es-PE';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { DetallefotoComponent } from './clientes/detallefoto/detallefoto.component';
import { LoginComponent } from './usuarios/login.component';
import { AuthGuard } from './usuarios/guards/auth.guard';
import { RoleGuard } from './usuarios/guards/role.guard';
import { TokenInterceptor } from './usuarios/interceptors/token.interceptor';
import { AuthInterceptor } from './usuarios/interceptors/auth.interceptor';



registerLocaleData(localeES,'es')

const route: Routes = [
  {path: '', redirectTo:'/clientes', pathMatch:'full'},
  {path: 'directivas', component: DirectivaComponent},
  {path: 'clientes', component: ClientesComponent},
  {path: 'clientes/page/:page', component: ClientesComponent},
  {path: 'clientes/form', component: FormularioComponent, canActivate:[AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'}},//canActivate:[AuthGuard]seguridad previa a enviar al endpoint del backend
  {path: 'clientes/form/:id', component: FormularioComponent, canActivate:[AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'}},
  {path: 'login', component: LoginComponent}
  //{path: 'clientes/ver/:id', component: DetallefotoComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    DirectivaComponent,
    ClientesComponent,
    FormularioComponent,
    PaginatorComponent,
    DetallefotoComponent,
    LoginComponent    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(route),
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatMomentDateModule
    //MatNativeDateModule
    
  ],
  providers: [ClienteService, 
    { provide: LOCALE_ID, useValue: 'es'},
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},],
    //esta clase se agrega para reemplazar el metodo privado agregarAuthorizationHeader()
  bootstrap: [AppComponent]
})
export class AppModule { }
