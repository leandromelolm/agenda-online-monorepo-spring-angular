import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AgendaService {

  private fcBehaviorSubject = new BehaviorSubject<any>(``);

  constructor() { }

  changeDateInFullcalendar(value : any){
    this.fcBehaviorSubject.next(value)
  }

  getDatePicker(){
    return this.fcBehaviorSubject;
  }
}
