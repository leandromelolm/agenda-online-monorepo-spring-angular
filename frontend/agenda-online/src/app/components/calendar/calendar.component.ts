import { Component, OnInit } from '@angular/core';
import { CalendarOptions } from '@fullcalendar/core';
import listPlugin from '@fullcalendar/list';
import interactionPlugin from '@fullcalendar/interaction';
import timeGridPlugin from '@fullcalendar/timegrid'
import dayGridPlugin from '@fullcalendar/daygrid';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
  
  calendarOptions? : CalendarOptions;
  initialview : string = "dayGridMonth";

  ngOnInit(): void {
   this.calendarRender(this.initialview)
  }

  calendarRender(initialview: string){
    this.calendarOptions = {
      plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin],
      editable: true,
      displayEventTime: true,
      initialView: initialview,  
      headerToolbar: {
        left: 'prev,next today',       
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },      
    };
  }

}
