import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { GameItemComponent } from './game-item/game-item.component';
import { GameDetailComponent } from './game-detail/game-detail.component';
import { DeathItemComponent } from './death-item/death-item.component';
import { ReasonItemComponent } from './reason-item/reason-item.component';
import { ReasonDetailComponent } from './reason-detail/reason-detail.component';
import { GameListComponent } from './game-list/game-list.component';
import { ReasonListComponent } from './reason-list/reason-list.component';
import { GameService } from './shared/game.service';
import { DeathService } from './shared/death.service';
import { ReasonService } from './shared/reason.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    NavbarComponent,
    GameItemComponent,
    GameDetailComponent,
    DeathItemComponent,
    ReasonItemComponent,
    ReasonDetailComponent,
    GameListComponent,
    ReasonListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [GameService, DeathService, ReasonService],
  bootstrap: [AppComponent]
})
export class AppModule { }
