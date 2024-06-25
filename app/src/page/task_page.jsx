import ItemList from "../component/item_list";
import BasicLayout from "../component/basic_layout";

export default function TaskPage() {
  return (
    <BasicLayout page='task'>
      <ItemList/>
    </BasicLayout>
  );
}