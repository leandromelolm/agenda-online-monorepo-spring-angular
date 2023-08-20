# Frontend

## Tecnologias usadas
- Angular CLI: 16.1.0
- Node: 18.16.0
- Package Manager: npm 9.5.1
- Fullcalendar 6.1.8


## Comandos
Instalar o CLI angular  
`npm install -g @angular/cli`

Instalar dependências com o comando (dentro da pasta raiz do projeto)  
`npm install`

Executar o projeto (na pasta raiz)  
`ng serve`

Parar o projeto com Ctrl+C

Matar o processo via terminal que ocupa a porta 4200  
`losf -i :4200`  
`kill <PID>`


### Comandos já executados no projeto
Criado o projeto com  
`ng new agenda-online`

Criado componente com  
`ng generate component components/calendar`

Comando usado para instalar dependências fullcalendar  
`npm install --save @fullcalendar/core @fullcalendar/angular @fullcalendar/daygrid @fullcalendar/interaction @fullcalendar/timegrid @fullcalendar/list`

Importado modulo fullcalendar no app.module.

Instalar dependência Material Angular para usar datepicker  
`ng add @angular/material`