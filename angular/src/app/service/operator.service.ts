import { Injectable } from '@angular/core';
import { Operator } from '../interfaces/operator.interface';

@Injectable({
  providedIn: 'root'
})
export class OperatorService {

  constructor() { }

  operatorList: Operator[] = [
    { id: 1,  name: 'Carlos Mendoza',    username: 'cmendoza',    password: 'Op3r@2024' },
    { id: 2,  name: 'Diana Ruiz',        username: 'druiz',       password: 'Diana#456' },
    { id: 3,  name: 'Felipe Torres',     username: 'ftorres',     password: 'Fel1pe!99' },
    { id: 4,  name: 'Gloria Pineda',     username: 'gpineda',     password: 'Glor!a22'  },
    { id: 5,  name: 'Hernán Castillo',   username: 'hcastillo',   password: 'Hern@n77'  },
    { id: 6,  name: 'Isabel Moreno',     username: 'imoreno',     password: 'Isab3l#1'  },
    { id: 7,  name: 'Jorge Salcedo',     username: 'jsalcedo',    password: 'Jorg3$88'  },
    { id: 8,  name: 'Karen Vega',        username: 'kvega',       password: 'Kar3n!55'  },
    { id: 9,  name: 'Luis Ángel Parra',  username: 'laparra',     password: 'Lu1s@ng3'  },
    { id: 10, name: 'Marcela Ortiz',     username: 'mortiz',      password: 'Marc3!44'  },
    { id: 11, name: 'Nicolás Herrera',   username: 'nherrera',    password: 'N1c0l@s9'  },
    { id: 12, name: 'Olga Jiménez',      username: 'ojimenez',    password: 'Olg@J!33'  },
    { id: 13, name: 'Pedro Alvarado',    username: 'palvarado',   password: 'P3dr0#66'  },
    { id: 14, name: 'Quintero Reyes',    username: 'qreyes',      password: 'Qu!nt3r0'  },
    { id: 15, name: 'Rosa Sandoval',     username: 'rsandoval',   password: 'Ros@$and'  },
    { id: 16, name: 'Santiago Blanco',   username: 'sblanco',     password: 'Sant!ago'  },
    { id: 17, name: 'Teresa Guzmán',     username: 'tguzman',     password: 'Ter3s@!1'  },
    { id: 18, name: 'Uriel Campos',      username: 'ucampos',     password: 'Uri3l@55'  },
    { id: 19, name: 'Valentina Cruz',    username: 'vcruz',       password: 'Val3nt!n'  },
    { id: 20, name: 'William Ospina',    username: 'wospina',     password: 'W1ll!@m0'  },
  ];

  selectAll() {
    return this.operatorList
  }

  selectById(id: number) {
    return this.operatorList.find(x => x.id == id)
  }

  update(id: number, data: Partial<Operator>) {
    const i = this.operatorList.findIndex(x => x.id == id)
    if(i === -1) return;

    this.operatorList[i] = {
      ...this.operatorList[i],
      ...data
    }
  }

  delete(id: number) {
    this.operatorList = this.operatorList.filter(x => x.id != id)
  }

  create(data: Operator) {
    this.operatorList.push(data)
  }
}
