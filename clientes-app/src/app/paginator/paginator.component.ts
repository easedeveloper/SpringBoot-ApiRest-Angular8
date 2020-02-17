import { Component, OnInit, Input, OnChanges, SimpleChange, SimpleChanges } from '@angular/core';

@Component({
  selector: 'paginator-nav',
  templateUrl: './paginator.component.html'
})
export class PaginatorComponent implements OnInit, OnChanges {


  @Input() paginator: any; // es como indica que inyecte el paginador en la clase hija
  paginas: number[];//un arreglo de numbers
  desde: number;
  hasta: number;

  constructor() { }

  ngOnInit() {
    this.initPaginator();
    /*this.desde = Math.min( Math.max(1, this.paginator.number-4), this.paginator.totalPages-5);
    this.hasta = Math.max( Math.min(this.paginator.totalPages, this.paginator.number+4), 6);

    if (this.paginator.totalPages > 5) {
      this.paginas = new Array(this.hasta - this.desde +1).fill(0).map((_valor, indice) => indice + this.desde)
    }else{
    //console.log("SOY EL NUMERO DE PAGINAS:",this.paginas = new Array(this.paginator.totalPages).fill(0).map((_valor, indice) => indice +1));
    this.paginas = new Array(this.paginator.totalPages).fill(0).map((_valor, indice) => indice +1)
    //_valor guion abajo para que no muestre el warning ya que no se va a utlizar 
    //map((valor es 0, indice parte de 0 hasta la cantidad de elementos))
    /*el map(), dentro del arreglo es para modificar los datos. En este caso para 
      modificar los ceros con los numeros de pagina
    
    se pasan el numero de elementos y lo tiene  @Input() paginador: any
    se utiliza fill(), para llenar el arreglo con datos!
    }
    */
  }

  ngOnChanges(changes: SimpleChanges){
    //Atraves de los changes podemos obtener el cambio del imput del objeto paginator
    let paginadorActualizado = changes['paginator'];
    if (paginadorActualizado.previousValue) {
      //solamente si tiene un estado anterior, haya cambiado
      this.initPaginator();
    }

    /*
    this.desde = Math.min( Math.max(1, this.paginator.number-4), this.paginator.totalPages-5);
    this.hasta = Math.max( Math.min(this.paginator.totalPages, this.paginator.number+4), 6);

    if (this.paginator.totalPages > 5) {
      this.paginas = new Array(this.hasta - this.desde +1).fill(0).map((_valor, indice) => indice + this.desde)
    }else{
    //console.log("SOY EL NUMERO DE PAGINAS:",this.paginas = new Array(this.paginator.totalPages).fill(0).map((_valor, indice) => indice +1));
    this.paginas = new Array(this.paginator.totalPages).fill(0).map((_valor, indice) => indice +1)
    //_valor guion abajo para que no muestre el warning ya que no se va a utlizar 
    //map((valor es 0, indice parte de 0 hasta la cantidad de elementos))
    /*el map(), dentro del arreglo es para modificar los datos. En este caso para 
      modificar los ceros con los numeros de pagina
    
    //se pasan el numero de elementos y lo tiene  @Input() paginador: any
    //se utiliza fill(), para llenar el arreglo con datos!
    }
    */
  }

  private initPaginator(): void {
    this.desde = Math.min( Math.max(1, this.paginator.number-4), this.paginator.totalPages-5);
    this.hasta = Math.max( Math.min(this.paginator.totalPages, this.paginator.number+4), 6);

    if (this.paginator.totalPages > 5) {
      this.paginas = new Array(this.hasta - this.desde +1).fill(0).map((_valor, indice) => indice + this.desde)
    }else{
    //console.log("SOY EL NUMERO DE PAGINAS:",this.paginas = new Array(this.paginator.totalPages).fill(0).map((_valor, indice) => indice +1));
    this.paginas = new Array(this.paginator.totalPages).fill(0).map((_valor, indice) => indice +1)
    //_valor guion abajo para que no muestre el warning ya que no se va a utlizar 
    //map((valor es 0, indice parte de 0 hasta la cantidad de elementos))
    /*el map(), dentro del arreglo es para modificar los datos. En este caso para 
      modificar los ceros con los numeros de pagina
    */
    //se pasan el numero de elementos y lo tiene  @Input() paginador: any
    //se utiliza fill(), para llenar el arreglo con datos!
    }
  }

}
