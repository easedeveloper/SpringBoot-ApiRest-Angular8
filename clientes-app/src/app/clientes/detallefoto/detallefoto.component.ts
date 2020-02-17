import { Component, OnInit, Input } from '@angular/core';
import { Cliente } from '../cliente';// accendiendo a al estructura
import { ClienteService } from '../cliente.service';
import { ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';
import { Route } from '@angular/compiler/src/core';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
//import { emit } from 'cluster';
import { AuthService } from 'src/app/usuarios/auth.service';

@Component({
  selector: 'app-detallefoto',
  templateUrl: './detallefoto.component.html',
  styleUrls: ['./detallefoto.component.css']
})
export class DetallefotoComponent implements OnInit {

  /*la idea es subir una foto por cliente, neceistamos el ID del cliente y el objeto cliente por eso necesitamso 
    un atributo cliente
  */
  @Input() cliente: Cliente; //estamos inyectando o colocando la instancia del cliente en detallecomponent
  titulo: string ="Detalle Del Cliente";
  private fotoseleccionada: File;
  progreso: number = 0;

  constructor( private clienteservice: ClienteService,
               private route: Router,
               private activateroute: ActivatedRoute,
               private modalservice: ModalService,
               private authService: AuthService) { }
  //activateroute: ActivatedRoute, poder susbcribir cuando cambia el parametro del ID

  ngOnInit() {
    //se esta quitando por usar el MODAL
    /*this.activateroute.paramMap.subscribe(params =>{
      let id:number = +params.get('id');
      if(id){
        this.clienteservice.getclientebyid(id)
        .subscribe((clienteargumento) =>//se obtiene le cliente por argumento            
            this.cliente = clienteargumento
          );
      }
    });
    */
  }

  seleccionarfoto(event){
    this.fotoseleccionada = event.target.files[0];
    this.progreso = 0;
    if (this.fotoseleccionada.type.indexOf('image') < 0){
      /*indexOf('image') del objeto String, que hace buscar en la cadena si hay una coincidencia con image, si la encuentra 
      va a retornar en la posicion en la uqe se encuentra, si no encuentra la cadena image retorna -1*/
      swal('Error Seleccionar Imagen:','El archivo tiene que ser de tipo Imagen','error');
      this.fotoseleccionada = null;
      /* se iguala en NULL para que no suba nada*/
    }
    console.log(this.fotoseleccionada);
    console.log("getclientid = ", this.cliente.a_id);
    console.log("soy el cliente ",this.cliente.region.r_name);
  }

  subirFoto(){
    if (!this.fotoseleccionada) {      
      swal('Error Upload:','debe seleccionar una foto','error');
    } else {
      this.clienteservice.subirfoto(this.fotoseleccionada, this.cliente.a_id)//1era parametro, la fotoseleccionad, 2do el ID= this.cliente tiene todo
      .subscribe(event => {
      //this.cliente = clientefoto;
      if (event.type === HttpEventType.UploadProgress) { // === sirve para validar por el valor y por el tipo de dato
        this.progreso = Math.round((event.loaded/event.total)*100);//calculando el porcentaje de la barra de progreso
      }else if(event.type === HttpEventType.Response){
        let response: any = event.body;
        this.cliente = response.cliente as Cliente;
        var aaa = this.cliente;
        
        this.modalservice.notificarupload.emit(this.cliente);
        //se va a emitir o notificar el cliente nuevo this.cliente que contiene la nueva foto
        swal('La foto se subido', `La foto subida con EXITO!!!: ${this.cliente.foto}`, 'success')
      }
      //this.route.navigate(['/clientes']);
    });
    }
  }

  cerrarmodal(){
    this.modalservice.cerrarmodal();
    this.fotoseleccionada = null;
    this.progreso = 0;
  }

}
