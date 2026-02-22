import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryService } from '../../core/services/inventory';
import { Inventory } from '../../core/models/inventory';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.html',
  standalone: true,
  imports: [CommonModule]
})
export class InventoryComponent implements OnInit {

    private inventoryService = inject(InventoryService);
  inventario: Inventory[] = [];

  juegoId:string='';

  ngOnInit(): void {
    this.cargarInventario();

    //escuchar eventos del juego
    window.addEventListener('gameItemFound', (event: any) => {
    const datosItem = event.detail;
    this.aniadirItemDesdeJuego(datosItem);
  });
  }

  aniadirItemDesdeJuego(datos: {nombre:string, detalle:string}): void {
    const nuevoItem: Inventory ={
        juegoId:this.juegoId,
        nombre: datos.nombre,
        detalle: datos.detalle
    };
    this.inventoryService.crearItem(nuevoItem).subscribe({
        next: () => {
            this.cargarInventario();
            console.log('¡Nuevo objeto registrado!')
        }
    })
  }

  cargarInventario(): void {
    this.inventoryService
      .getMyInventory()
      .subscribe({
        next: (data) => (this.inventario = data),
        error: (err) => console.error('Error al cargar el inventario', err)
      });
  }

  aniadirItem(): void {
    const nuevoItem: Inventory = {
      juegoId: this.juegoId,
      nombre: 'Jarra de cerámica',
      detalle: 'Conseguido tras completar elección 9'
    };

    this.inventoryService.crearItem(nuevoItem)
      .subscribe({
        next: (itemCreado) => {
            console.log('Item ID :', itemCreado.id)
            this.cargarInventario();
        },
        error:(err) => console.error('No obtuviste el objeto', err)
      });
  }

  eliminarItem(itemId: string | undefined): void {
    if (!itemId) return;

    this.inventoryService.eliminarItem(itemId)
        .subscribe({
            next: () => this.cargarInventario(),
            error: (err) => console.error('Error al eliminar objeto', err)
        });
    }
}
