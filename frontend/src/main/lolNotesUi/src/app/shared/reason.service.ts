import { Injectable } from '@angular/core';
export class Reason { 
  constructor(
    public id: number, 
    public description: string
  ){
  }
}
@Injectable({
  providedIn: 'root'
})
export class ReasonService {

  constructor() { }

}

const reasons = [
  {
    "id": 100,
    "description": "OutNumbered"
  },
  {
    "id": 104,
    "description": "Positionning"
  },
  {
    "id": 101,
    "description": "Jgl gank"
  },
  {
    "id": 102,
    "description": "Over Staying"
  },
  {
    "id": 103,
    "description": "Match-up"
  }
]; 
