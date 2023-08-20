import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CalendarComponent } from './components/calendar/calendar.component';
import { AgendaComponent } from './pages/agenda/agenda.component';

const routes: Routes = [
  {path: '', redirectTo: 'agenda', pathMatch: 'full'},
  {path: 'agenda', component: AgendaComponent},
  {path: 'calendar', component: CalendarComponent},
  {path: '**', redirectTo: 'agenda'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
