import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { Region } from './region';


@Component({
  selector: 'app-formulario',
  templateUrl: './formulario.component.html'
})
export class FormularioComponent implements OnInit {

  private clien : Cliente = new Cliente();
  regiones: Region[];
  private titulo: string = "Crear Cliente";
  private errores: string[];

  constructor(private servclie: ClienteService, private router: Router, private activateroute: ActivatedRoute) { }

  ngOnInit() {
    this.cargarcliente();
  }

  cargarcliente(): void{
    this.activateroute.params
    .subscribe(params =>{
      let id = params['id']
      if(id){
        this.servclie.getclientebyid(id)
        .subscribe((cliente) => 
          this.clien = cliente
        )
      }
    });
    this.servclie.getRegiones().subscribe(region => this.regiones = region);
  }

  public CrearAlumno():void{
    console.log(this.clien);
    this.servclie.crearalumno(this.clien)
    .subscribe(clien => {
        this.router.navigate(['/clientes'])
        swal('Nuevo Cliente', `El cliente ${this.clien.a_name+" "+ this.clien.a_lastname} ah sido creado con Exito`,'success')
      },
      err =>{
        this.errores = err.error.errors as string[];
        console.log('Codigo del error desde el Backed '+ err.status);
        console.log(err.error.errors);
      }
      )
  }

  updateclientes():void{
    console.log(this.clien);
    this.servclie.updatecliente(this.clien)
    .subscribe(json => {
        this.router.navigate(['/clientes'])
        swal('Cliente Actualizado', `${json.msjexito}: ${this.clien.a_name+" "+ this.clien.a_lastname}`,'success');
      },
      err =>{
        this.errores = err.error.errors as string[];
        console.log('Codigo del error desde el Backed '+ err.status);
        console.log(err.error.errors);
      }
    )
  }

  compararRegion(o1:Region, o2:Region):boolean{
  if (o1 === undefined && o2 === undefined){
    return true;
  }
    return o1 === null || o2 === null || o1 === undefined || o2 === undefined ? false : o1.r_id === o2.r_id;
  }  
  
}
