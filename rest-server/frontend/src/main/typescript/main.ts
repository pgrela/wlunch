import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {AppModule} from "./app.module";

export const platformRef = platformBrowserDynamic();

export function main() {
    return platformRef.bootstrapModule(AppModule)
        .catch((err: Error) => console.error(err));
}
var x: number = 9;
x *= 99;
console.log("This code is run");
// support async tag or hmr
switch (document.readyState) {
    case 'interactive':
    case 'interactive2':
    case 'complete':
        main();
        break;
    case 'loading':
    default:
        document.addEventListener('DOMContentLoaded', () => main());
}

