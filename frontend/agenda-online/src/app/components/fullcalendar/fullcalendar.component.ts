import { Component, OnInit, ViewChild } from '@angular/core';
import { FullCalendarComponent } from '@fullcalendar/angular';
import {CalendarOptions} from '@fullcalendar/core'
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';

@Component({
  selector: 'app-fullcalendar',
  templateUrl: './fullcalendar.component.html',
  styleUrls: ['./fullcalendar.component.css']
})
export class FullcalendarComponent implements OnInit {
  
  @ViewChild('fullcalendar') fullcalendar?: FullCalendarComponent; 
  calendarOptions? :CalendarOptions;

  ngOnInit(): void {
    this.renderFullCalendar()
  }

  renderFullCalendar(){
    this.calendarOptions = {
      plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin],
      initialView: 'timeGridDay'
    }
  }
}
