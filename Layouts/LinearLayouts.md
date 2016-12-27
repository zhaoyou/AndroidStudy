### gravity
> arranges the content inside the view.
### layout_gravity
> arranges the view's position outside of itself.


- Don't use gravity/layout_gravity with a RelativeLayout. Use them for Views in LinearLayouts and FrameLayouts.
The view's width (or height) has to be greater than its content. Otherwise gravity won't have any effect. Thus, wrap_content and gravity are meaningless together.
- The view's width (or height) has to be less than the parent. Otherwise layout_gravity won't have any effect. Thus, match_parent and layout_gravity are meaningless together.
- The layout_gravity=center looks the same as layout_gravity=center_horizontal here because they are in a vertical linear layout. You can't center vertically in this case, so layout_gravity=center only centers horizontally.
