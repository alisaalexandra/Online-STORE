import {Component, OnInit} from '@angular/core';
import {Product} from '../model/product';
import {ProductService} from '../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {OrderService} from '../../../orders/service/order.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[];

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router,
              private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.getProducts();
  }

  // tslint:disable-next-line:typedef
  getProducts() {
    this.productService.findAll().subscribe(data => {
      this.products = data;
    });
  }

  // tslint:disable-next-line:typedef
  deleteProduct(id: number) {
    this.productService.delete(id).subscribe(data => {
      this.getProducts();
    });
  }

  // tslint:disable-next-line:typedef
  editProduct(id: number) {
    this.router.navigate(['product-edit', id]);
  }

  // tslint:disable-next-line:typedef
  addProduct() {
    this.router.navigate(['product-add']);
  }

  // tslint:disable-next-line:typedef
  addToCart(productID: number) {
    this.orderService.addToCart('alisa', productID).subscribe(data => {
      this.getProducts();
    });
  }
}
