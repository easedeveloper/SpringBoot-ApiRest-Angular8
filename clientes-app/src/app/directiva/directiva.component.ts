import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-directiva',
  templateUrl: './directiva.component.html',
})
export class DirectivaComponent {

  listacurso: String[] = ['TypeScript', 'JavaScript', 'Java SE' ,'C#', 'PHP'];

  habilitar: boolean = true;

  constructor() { }

  sethabilitar(): void{
    //se utiliza this. porque es un atributo de la clase
    this.habilitar = (this.habilitar == true)?false: true;
  }
}
