import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { AgendaComponent } from './pages/agenda/agenda.component';
import { FullcalendarComponent } from './components/fullcalendar/fullcalendar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker'
import {MatDialogModule} from '@angular/material/dialog';
import { DatepickerComponent } from './components/datepicker/datepicker.component';

@NgModule({
  declarations: [
    AppComponent,
    CalendarComponent,
    AgendaComponent,
    FullcalendarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FullCalendarModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatDialogModule,
    DatepickerComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
