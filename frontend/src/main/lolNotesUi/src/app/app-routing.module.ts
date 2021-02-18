import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';


import { GameDetailComponent } from './game-detail/game-detail.component';
import { ReasonDetailComponent } from './reason-detail/reason-detail.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'reasons/:reasonId', component: ReasonDetailComponent},
  {path: 'games/:gameId', component: GameDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
