import { Injectable } from '@angular/core';
export class Game {
  constructor(
    public id: number, 
    public createdOn: Date, 
    public role: string, 
    public champion: string
  ) {}
}
@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor() { }

  getGames(): Game[] { 
    return games.map(g => new Game(g.id, g.createdOn, g.role, g.champion)); 
  }

  getGameById(gameId: number): Game{ 
    return games.find(g => g.id === gameId)!; 
  }


  
}

const games = [
  {
    "id" : 105, 
    "createdOn": new Date("2021-02-18T16:52:27.290+00:00"),
    "role": "ADC",
    "champion": "KAISA"
  },
  {
    "id" : 106, 
    "createdOn": new Date("2021-02-19T16:52:27.290+00:00"),
    "role": "ADC",
    "champion": "TRISTANA"
  },
  {
    "id" : 107, 
    "createdOn": new Date("2021-02-19T19:00:10.230+00:00"),
    "role": "ADC",
    "champion": "KAISA"
  }

]
