import SwiftUI
import shared

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            ViewStateScreen()
		}
	}
	init() {
	    let koinInitializer = KoinInitializer()
        koinInitializer.doInitKoin()
    }
}
