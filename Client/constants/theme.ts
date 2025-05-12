export const colors = {
  // Main Colors
  blue: "#007BFF",
  white: "#FFFFFF",

  // Blue Shades
  lightBlue: "#66B3FF", // Lighter blue
  darkBlue: "#0056b3", // Darker blue
  blueGray: "#2F5D80", // A grayish blue

  // White Shades
  offWhite: "#F7F7F7", // Slightly off-white
  lightGray: "#D3D3D3", // Light gray used as background or border

  // Red Shades
  red: "#FF0000", // Standard red
  darkRed: "#B22222", // Dark red
  lightRed: "#FF6347", // Light red (tomato color)

  // Black Shades
  black: "#000000", // Standard black
  darkBlack: "#1C1C1C", // Very dark black
  lightBlack: "#333333", // Light black (dark gray)
};

export const zIndexes = {
  background: 0, // Lowest layer, used for background elements
  content: 1, // Default layer for regular content
  header: 10, // Above content but below modals, typically for page headers
  modal: 100, // Layer for modal windows and overlays, should be above content
  overlay: 200, // Overlay elements that cover content, such as darkened backgrounds behind modals
  dropdown: 300, // Dropdown menus and tooltips, placed above modal/overlay
  toast: 400, // Toast notifications, should be displayed on top of most elements
  tooltip: 500, // Highest layer for tooltips, ensuring they appear above all other elements
};
