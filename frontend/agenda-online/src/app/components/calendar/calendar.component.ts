import { Component, OnInit, ViewChild } from '@angular/core';
import { CalendarOptions, DateSelectArg } from '@fullcalendar/core';
import listPlugin from '@fullcalendar/list';
import interactionPlugin, { DateClickArg } from '@fullcalendar/interaction';
import timeGridPlugin from '@fullcalendar/timegrid'
import dayGridPlugin from '@fullcalendar/daygrid';
import { FullCalendarComponent } from '@fullcalendar/angular';
import { INITIAL_EVENTS, createEventId } from '../../data/event-utils';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  calendarOptions? : CalendarOptions;
  initialview : string = "dayGridMonth";
  @ViewChild('fullcalendar') fullcalendar?: FullCalendarComponent;

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
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
      },
      events: INITIAL_EVENTS,
      dateClick: this.handleDateClick.bind(this),
    };
  }

  handleDateClick(arg: DateClickArg) {
    if (arg.view.type == "dayGridMonth") {
      this.changeCalendarView(arg);
    }
    if (arg.view.type != "dayGridMonth") {
      const title = prompt(arg.dateStr.slice(11,19) +' - Please enter a new title for your event');
      const calendarApi = arg.view.calendar;
      console.log(arg);
      // calendarApi.unselect(); // clear date selection
      let end = new Date(arg.date)
      end.setMinutes(arg.date.getMinutes() + 30);

      if (title) {
        calendarApi.addEvent({
          id: createEventId(),
          title,
          start: arg.date,
          end: end
        });
      }
    }
  }

  changeCalendarView(arg: any) {
    let calendar = this.fullcalendar?.getApi();
    calendar?.changeView("timeGridDay", arg.date);
  }
  
}
