import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Bienvenido a Angular';
  curso1 = 'Curso 5 con Spring y angular 7';
  curso2: String = ' Andres Guzman';
}
