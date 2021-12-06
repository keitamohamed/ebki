import './App.css';
import './style/at_responsive.css'

import {DynamicRoute} from "./route/DynamicRoute";
import {CarProvider} from "./context/CarProvider";
import {DashboardProvider} from "./context/DashboardProvider";
import {AuthProvider} from "./context/AuthProvider";

function App() {
    return (
        <AuthProvider>
            <DashboardProvider>
                <CarProvider>
                    <DynamicRoute/>
                </CarProvider>
            </DashboardProvider>
        </AuthProvider>
    );
}

export default App;
