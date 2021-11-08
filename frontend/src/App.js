import './App.css';
import './component/style/at_responsive.css'

import {DynamicRoute} from "./route/DynamicRoute";
import {CarProvider} from "./context/CarProvider";
import {DashboardProvider} from "./context/DashboardProvider";

function App() {
    return (
        <DashboardProvider>
            <CarProvider>
                <DynamicRoute/>
            </CarProvider>
        </DashboardProvider>
    );
}

export default App;
