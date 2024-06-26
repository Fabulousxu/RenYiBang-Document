import BasicLayout from "../component/basic_layout";
import Chat from "../service/Chat";

export default function MessagePage() {
  return (
    <BasicLayout page='message'>
      <Chat />
    </BasicLayout>
  );
}