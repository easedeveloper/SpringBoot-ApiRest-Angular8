import { Injectable, EventEmitter } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class ModalService {

  modal: boolean = false;
  private _notificarupload = new EventEmitter<any>();
  // en typeScript para definir su metodo getter se coloca un guin abajo _notificarupload
  constructor() { }

  get notificarupload(): EventEmitter<any>{
    return this._notificarupload;
  }

  abrirmodal(){
    this.modal = true;
  }
  cerrarmodal(){
    this.modal = false;
  }
}
