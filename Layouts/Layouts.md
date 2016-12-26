### Layout

> ID "@+id/name" 第一次定义一个变量  "@android:id/name" 引用系统android framework的变量

> android:id="@[+][package:]id/resource_name"

#### Re-using Layouts
> Note: The tools:showIn attribute in the XML above is a special attribute that is removed during compilation and used only at design-time in Android Studio—it specifies a layout that includes this file, so you can preview (and edit) this file as it appears while embedded in a parent layout.


#### 线性布局 LinearLayout

#### 相对布局RelativeLayout

##### 适配器 Adapter

  - ArrayAdapter

> 实现自定义的View Item ，继承ArrayAdapter， 重写getView()方法

  - SimpleCursorAdapter

>  notifyDataSetChanged()

#### Handing click events
> setItemClickListener

