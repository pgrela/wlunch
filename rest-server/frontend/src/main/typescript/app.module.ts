import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {Restaurants} from "./restaurants/restaurants.component";

@NgModule({
    imports: [BrowserModule],
    declarations: [Restaurants],
    bootstrap: [Restaurants]
})
export class AppModule {
}
