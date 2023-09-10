import { Component, OnInit, ViewChild } from '@angular/core';
import { FullCalendarComponent } from '@fullcalendar/angular';
import {CalendarOptions} from '@fullcalendar/core'
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import { Subscription } from 'rxjs';
import { AgendaService } from 'src/app/service/agenda.service';

@Component({
  selector: 'app-fullcalendar',
  templateUrl: './fullcalendar.component.html',
  styleUrls: ['./fullcalendar.component.css']
})
export class FullcalendarComponent implements OnInit {
  
  @ViewChild('fullcalendar') fullcalendar?: FullCalendarComponent; 
  calendarOptions? :CalendarOptions;

  eventSubcription! : Subscription;

  constructor(
    private agendaService : AgendaService
  ){}

  ngOnInit(): void {
    this.renderFullCalendar();
    this.eventSubcription = this.agendaService.getDatePicker()
      .subscribe(value =>{
        console.log(value);
        this.changeFCView(value);        
      });
  }

  renderFullCalendar(){
    this.calendarOptions = {
      plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin],
      initialView: 'timeGridDay',
      headerToolbar: {
        left: '',       
        center: '',
        right: ''
      },    
    }
  }

  changeFCView(arg : any){
    let calendarApi = this.fullcalendar?.getApi();
    calendarApi?.changeView("timeGridDay", arg)
  }
}
