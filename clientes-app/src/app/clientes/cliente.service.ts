import { Injectable } from '@angular/core';
import { formatDate, DatePipe } from '@angular/common';
//import localeES from '@angular/common/locales/es-PE';
import { clientes } from './clientes.json';
import { Cliente } from './cliente.js';
import { of,Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent } from '@angular/common/http';
import { map,catchError, tap } from 'rxjs/operators'; // catcError se encarga de interseptar el flujo si falla
//import swal from 'sweetalert2';
import { Router } from '@angular/router';
import { Region } from './region.js';
//import { AuthService } from '../usuarios/auth.service.js';

@Injectable()
export class ClienteService {

  private urlEndPoint: string = 'http://localhost:9090/api/clientes';

  /* por constructor se va a pasar el content type. Variable reemplazada por usuarios/interceptos/token.interceptor.ts
    private httpheaders = new HttpHeaders({'Content-Type': 'application/json'})
  */
  constructor(private http: HttpClient, private router: Router,
    /*private authservice: AuthService*/){}

/* Este metodo a sido reemplazado por usuarios/interceptos/token.interceptor.ts, por lo tanto 
  se eliminara los atributos headers {headers: this.AgregarAuthorizationHeader()} de cada metodo
  private AgregarAuthorizationHeader(){
    //este metodo lo vamos a llamar en cada llamada a los endpoints hacia nuestras rutas protegidas 
    let token = this.authservice.token;
    obtenemos el token, este el metodo getter que accede al token va a buscar al atributo privado _token, si no
    lo encuentra va a buscarlo en el sessionstorage
    
   if (token != null) {
     return this.httpheaders.append('Authorization', 'Bearer ' + token);//crea una nueva instancia con este cambio
   }
     return this.httpheaders;
  }
*/
/* este metodo se esta reemplazando por el metodo global auth.interceptor.ts
  private isnNoAutorizado(e): boolean{
    if(e.status==401){

      if (this.authservice.isAuthenticated()) {
        this.authservice.logout();
      }

      this.router.navigate(['/login']);
      return true;
      //no esta autorizado y redirigir a login
    }

    if(e.status==403){
      swal('Acceso Denegado',`HOla ${this.authservice.usuario.username} no tienes acceso a este Recurso!`,'warning');
      this.router.navigate(['/clientes']);
      return true;
      //no esta autorizado y redirigir a login
    }
    return false;

    el codigo de error HTTP 401 Unauthorized (No autorizado) indica que la peticion(Request) no ha sido ejecutado
    *porque carece de credenciales validas de autenticacion.
    *HTTP 403 Forbidden(prohibido) en respuesta a un cliente de una pagina web o servicio, indica que le servidor se niega
    *a permitir la accion solicitada. En otras paalbras el servidor ah denegado acceso
    
  }
  */

  getRegiones(): Observable<Region[]>{
    return this.http.get<Region[]>(this.urlEndPoint + '/region');/*.pipe(
                                                            /*,{headers: this.AgregarAuthorizationHeader()}
      catchError(e => {
        //this.isnNoAutorizado(e); 
        return throwError(e);
      })
    );
    este metodo se esta reemplazando por el metodo global auth.interceptor.ts
    */
  }

  //ngOnInit(){}
  getClientes(page: number): Observable<any>{//Cliente[]
    // se convertira en un flujo de datos stream, en tiempo real, asincrona y reactiva
    // se le tiene que devolver un observable, se le hace un cast 
    //return this.http.get<Cliente[]>(this.urlendpoint);
    //console.log("HOLAAAAA: "+this.http.get(this.urlEndPoint).pipe(
    // map((response) => response as Cliente[])));
    return this.http.get(this.urlEndPoint +'/page/'+ page).pipe(
      tap((response: any) => {//tap(response => {}) //el Tap nos sirve para,Trabajar con los datos y realizar algun tipo de Tarea
      //response ya no es un object, ahora es de cualquier Tipo  
      //let clientess = response as Cliente[];
        //console.log('ClienteService: Tap 1')
        (response.content as Cliente[]).forEach(clientef =>{//Cast se refiere a conversion de tipos de datos
          //para que el content se pueda leer, acceder se cambia el tipo de dato del response
          console.log("TAP: NOMBRE CLIENTEF= ",clientef.a_name);
          /*Si Grabamos Lanza un error, porque estamos retornando el JSON completo anterior con todos los clientes y no tiene
          el content, al no estar definido el foreach no es un metodo y se cae se tiene que actualizar la ruta +'/page/'+ page */
        })
      }),
      //map((response) => response as Cliente[]) // esta es la forma de typescript, el map convierte el tipo object, a un tipo cliente[]
      //map(function(response){ return response as Cliente[]}) esta es la forma antigua
      map((response: any) => {
        //let clientes = response as Cliente[]
        //return clientes.map(clien =>{
          (response.content as Cliente[]).map(clien =>{
          clien.a_tutor = clien.a_tutor.toLowerCase();
          console.log("Map - Miniscula - Tutor= ",clien.a_tutor)
           //let date: any = formatDate(clien.a_fechanaci,'dd-MM-yyyy','en-US'); // recibe 3 argumentos 1 la fecha original, 2 el patron de formato que le queremos dar, 3 el Localidad
           //clien.a_fechanaci = date;
           //registerLocaleData(localeES,'es')
           //let datepipe = new DatePipe('es-PE');
           //let date: any = datepipe.transform(clien.a_fechanaci,'fullDate');//'EEEE dd, MMMM yyyy' // recibe 3 argumentos 1 la fecha original, 2 el patron de formato que le queremos dar, 3 el Localidad
           //clien.a_fechanaci = date;
           return clien;
        });
        return response;
      }),
      tap(responsee => {
        //console.log('ClienteService: Tap 2')
        (responsee.content as Cliente[]).forEach(clientef =>{
          console.log("Response tap-3 - Nombre = ",clientef.a_name);
        })
      })
    );
    //return of(clientes); 
  }
                            //se cambia a any ya que el catchError esta devolviendo un JSON         
  crearalumno(clie: Cliente): Observable<Cliente>{
    //se invoca al httpclient tipo post y como 1er parametro la URi, 2do el objeto Cliente, 3er las cabeceras http
    return this.http.post(this.urlEndPoint, clie).pipe(
                                             /*,{headers: this.AgregarAuthorizationHeader()}*/
    //{headers: this.httpheaders} este metodo esta desprotegido  
    //retorname el response:any(que es un json) del mapeo como el Objeto Cliente
      map((response: any) => response as Cliente), 
      //response.clie, se toma el atributo cliente del JSON y lo convertimos a Cliente
      catchError(e =>{

        /* este metodo se esta reemplazando por el metodo global auth.interceptor.ts
        if (this.isnNoAutorizado(e)) {
          return throwError(e);
          /*si ocurre este error, retorna un 401 o 403 simplemente retornamos el true error, ya que se detecta que no hay
          * hay autorizacion, los permisos para acceder a este recurso         
        }
        */
        if (e.status==400) {
          return throwError(e);
        }
        if (e.error.mensaje) {
          console.error(e.error.msjerror);          
        }        
        //swal(e.error.msjerror, e.error.error, 'error'); este metodo se esta reemplazando por el metodo global auth.interceptor.ts
        return throwError(e);
      })
    )
  }
  
  getclientebyid(id): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
                                                          /*,{headers: this.AgregarAuthorizationHeader()}*/
      catchError(e => {
        /* este metodo se esta reemplazando por el metodo global auth.interceptor.ts
        if (this.isnNoAutorizado(e)) {
          return throwError(e);
          si ocurre este error, retorna un 401 o 403 simplemente retornamos el true error, ya que se detecta que no hay
          * hay autorizacion, los permisos para acceder a este recurso 
          
        }
        */
       if (e.status != 401 && e.error.msjerror) {
        this.router.navigate(['/clientes']);
        console.error(e.error.msjerror);
       }        
        //swal('Error al Editar', e.error.msjerror, 'error'); este metodo se esta reemplazando por el metodo global auth.interceptor.ts
        return throwError(e);
      })
    )
  }

  updatecliente(clie: Cliente): Observable<any>{
    //String de interpolacion `` es la doble comillas invertidas
    //console.log(this.http.put<Cliente>(`${this.urlEndPoint}/${clie.a_id}`, clie,{headers: this.httpheaders}))
    return this.http.put<any>(`${this.urlEndPoint}/${clie.a_id}`,clie).pipe(
                                                                  /*,{headers: this.AgregarAuthorizationHeader()}*/
      //{headers: this.httpheaders} metodo desprotegido
      map((response: any) => response as Cliente),
      catchError(e =>{

        /* este metodo se esta reemplazando por el metodo global auth.interceptor.ts
        if (this.isnNoAutorizado(e)) {
          return throwError(e);
          si ocurre este error, retorna un 401 o 403 simplemente retornamos el true error, ya que se detecta que no hay
          * hay autorizacion, los permisos para acceder a este recurso          
        }
        */

        if (e.status==400) {
          return throwError(e);
        }
        if (e.error.mensaje) {
          console.error(e.error.msjerror);          
        }  
        //console.error(e.error.msjerror);
        //swal(e.error.msjerror, e.error.error, 'error'); este metodo se esta reemplazando por el metodo global auth.interceptor.ts
        return throwError(e);
      })
    )
  }

  deleteclientservice(id: number): Observable<Cliente>{
    return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
                                                            /*,{headers: this.AgregarAuthorizationHeader()}*/
      // {headers: this.httpheaders} metodo desprotegido
      catchError(e =>{

        /*este metodo se esta reemplazando por el metodo global auth.interceptor.ts
        if (this.isnNoAutorizado(e)) {
          return throwError(e);
          si ocurre este error, retorna un 401 o 403 simplemente retornamos el true error, ya que se detecta que no hay
          * hay autorizacion, los permisos para acceder a este recurso           
        }
        */

       if (e.error.mensaje) {
        console.error(e.error.msjerror);          
      } 
        //swal(e.error.msjerror, e.error.error, 'error');este metodo se esta reemplazando por el metodo global auth.interceptor.ts
        return throwError(e);
      })
    )
  }

  subirfoto(archivo: File, id): Observable<HttpEvent<{}>> {
  //subirfoto(archivo: File, id): Observable<Cliente> {
    //se va a subir una foto asignada al cliente
    let formdata = new FormData();
    formdata.append("archivo", archivo);//mismo nombre que le pusimos en el back-end @RequestParam("archivo")
    formdata.append("id", id);
    /*Este metodo a sido reemplazado por usuarios/interceptos/token.interceptor.ts, por lo tanto 
     se eliminara los atributos headers {headers: this.AgregarAuthorizationHeader()} de cada metodo
    let httpHeaders = new HttpHeaders();
    let token = this.authservice.token;
    if (token != null) {
      httpHeaders = httpHeaders.append('Authorization', 'Bearer'+ token);
      //es inmultable, crea una nueva instancia
    }
    */
    const req = new HttpRequest('POST', `${this.urlEndPoint}/upload`, formdata, {
      reportProgress: true
      //headers: httpHeaders
    });
    
    //return this.http.post(`${this.urlEndPoint}/upload`, formdata).pipe(
      return this.http.request(req)/*.pipe(
        catchError(e => {
          this.isnNoAutorizado(e);
          return throwError(e);
         ** Este metodo se esta reemplazando por el metodo global auth.interceptor.ts **
        })
      );*/ /* .pipe(
      map((response: any) => response.cliente as Cliente),//aqui se va a emitir un response con nuestro json
      */
      //se tiene que acceder a este atributo cliente, para convertir en un observable de clientes 
      /*catchError(e => {
        //this.router.navigate(['clientes']);
        console.error(e.error.msjerror);
        swal(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
        
      })
    );*/
    // el siguiente paso es convertir nuestro flujo(observable) para que sea del tipo cliente, se utiliza el pipe
    // aca se tiene que manejar el observable del tipo cliente para poder obtener cliente modificado con la foto

  } 


  //private urlEndPoint: string = 'http://localhost:9090/api/clientes';
}
