import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import swal from 'sweetalert2';
import { tap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { clientes } from './clientes.json';
import { ModalService } from './detallefoto/modal.service';
import { AuthService } from '../usuarios/auth.service';


@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
})
export class ClientesComponent implements OnInit {

  clien: Cliente[];
  paginador: any;
  clienteSeleccionado: Cliente;

  constructor(private ServClien: ClienteService,
              private activateroute: ActivatedRoute,
              private modalservice: ModalService,
              private authService: AuthService) { 
              //Se importan y se inyectan en el costructor para poder utilizarlo en la vista   
              }

  ngOnInit() {
    
    this.activateroute.paramMap.subscribe( params => {//paramMap se encarga de suscribir un obsevador
      let page: number = +params.get('page');//el operador de + convierte el String en Integer
      // para la primera pagina que seria la pag. cero el paramentro page no existe en cuanto no exista, lo vamos a asignar a 0

      if (!page) {// si page no esta definido
        page = 0;
      }
    this.ServClien.getClientes(page).pipe(
      tap(//clientesc => this.clien = clientesc // el valor cliente al atributo cliente
        //clientesc =>{
        response =>{
        //this.clien = clientesc;
        //console.log('clientesComponent: tap 3')
        //clientesc.forEach(clientefor =>{
        (response.content as Cliente[]).forEach(clientefor =>{
          console.log("Componente Tap-4 - Nombres: ",clientefor.a_name)
        });
      }),
    ).subscribe(response =>{
        this.clien = response.content as Cliente[];
        this.paginador = response; // los valores que contiene el response ahora va a ser del paginador
      });
  });
  this.modalservice.notificarupload.subscribe(clienoti =>{
    /*Se recorre el listado clientes a traves del map, preguntamos si el cliente id de cada cliente de la tabla 
     this.clien es igual al cliente id de clienoti(detallefoto.component.ts) que estamos emitiendo si son iguales le cambiamos la imagen son iguales*/
     this.clien = this.clien.map(clienteoriginal =>{
      //this.clien.map(), nos permite por cada cliente cambiar algo
      if (clienoti.a_id == clienteoriginal.a_id) {
        clienteoriginal.foto = clienoti.foto;        
      }
      return clienteoriginal;
     })
     
  })
    //.subscribe(clientess => this.clien = clientess);  Es un metodo que nos permite suscribirnos dentro del flujo
    // si el subscribe el observable no se ejecuta, no hay items que se transmita
    //.subscribe((clienn)=>{this.clien = clienn});
  }

  deletecliente(clie: Cliente): void{
    swal.fire({ 
      title: 'Estas seguro?',
      text: `Esta seguro que desea Eliminar al Cliente ${clie.a_name} ${clie.a_lastname} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, Eliminar!'
    }).then((result) => {
      if (result.value) {
        this.ServClien.deleteclientservice(clie.a_id).subscribe(response => {
            this.clien = this.clien.filter(cli =>
              cli !== clie);
            swal(
              'Cliente Eliminado!',
              `Cliente ${clie.a_name} Eliminado con exito `,
              'success'
            )
          }
        )
      }
    })
  }

  abrirModal(cliente: Cliente){
    this.clienteSeleccionado = cliente;
    this.modalservice.abrirmodal();
  }


}
