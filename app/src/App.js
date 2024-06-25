import {Navigate, Route, Routes} from 'react-router-dom';
import TaskPage from "./page/task_page";
import ServicePage from "./page/service_page";
import HelpPage from "./page/help_page";
import MessagePage from "./page/message_page";
import ProfilePage from "./page/profile_page";
import OrderPage from "./page/order_page";
import OrderDetailPage from "./page/order_datail_page";

export default function App() {
  return (
    <Routes>
      <Route index={true} element={<Navigate to='/task'/>}/>
      <Route path='/task' element={<TaskPage/>}/>
      <Route path='/service' element={<ServicePage/>}/>
      <Route path='/help' element={<HelpPage/>}/>
      <Route path='/order' element={<OrderPage/>}/>
      <Route path='/order/:id' element={<OrderDetailPage/>}/>
      <Route path='/message' element={<MessagePage/>}/>
      <Route path='/profile' element={<ProfilePage/>}/>
    </Routes>
  );
}