import './App.css';
import './component/style/at_responsive.css'

import {DynamicRoute} from "./route/DynamicRoute";
import {CarProvider} from "./context/CarProvider";

function App() {
    return (
        <CarProvider>
            <DynamicRoute/>
        </CarProvider>
    );
}

export default App;
